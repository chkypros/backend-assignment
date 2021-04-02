"use strict";

const Axios = require('axios')
const StateMachine = require('javascript-state-machine');
const MockCDR = require("./cdr_response")
const Settings = require("./settings")
const PhoneNumbers = require("./phone_numbers")
const Helpers = require("./helper")

const startCall = () => {
    console.debug('Initialize webhook ...');
    initialize();
    return { status: 'firing webhooks' };
}

const ACTIONS = {
    START: 'START',
    ORIGINATE: 'ORIGINATE',
    RING: 'RING',
    ANSWERED: 'ANSWERED',
    HANGUP: 'HANGUP'
}

const DIRECTIONS = {
    OUTBOUND: 'outbound',
    INBOUND:  'inbound'
}

let FSM =  StateMachine.factory({
    init: ACTIONS.START,
    transitions: [
      { name: 'toOriginate',           from: ACTIONS.START,     to: ACTIONS.ORIGINATE },
      { name: 'toRing',                from: ACTIONS.ORIGINATE, to: ACTIONS.RING },
      { name: 'toAnswer',              from: ACTIONS.RING,      to: ACTIONS.ANSWERED },
      { name: 'toHangup',              from: ACTIONS.ANSWERED,  to: ACTIONS.HANGUP },
      { name: 'toNotAnswered',         from: ACTIONS.RING,      to: ACTIONS.HANGUP },
      { name: 'toDevNullFromRing',     from: ACTIONS.RING,      to: ACTIONS.DEVNULL },
      { name: 'toDevNullFromAnswered', from: ACTIONS.ANSWERED,  to: ACTIONS.DEVNULL }
    ],
    data: function(caller, callee) {
        return {
            cdr: {
                uuid: Helpers.uuidv4(),
                domain_name: Settings.domain,
                caller_name: "",
                caller_number: caller.number,
                destination_number: callee.number,
                direction: DIRECTIONS.OUTBOUND,
                call_start: null,
                ring_start: null,
                answer_start: null,
                call_end: null,
                duration: null,
                recording: Helpers.uuidv4(),
                click_to_call: false,
                click_to_call_data: "",
                action: ACTIONS.ORIGINATE        
            }
        }
    },
    methods: {
      onToOriginate:           function() { this.sendOriginate(); },
      onToRing:                function() { this.sendRinging(); },
      onToAnswer:              function() { this.sendAnswer(); },
      onToHangup:              function() { this.sendHangup(); },
      onToNotAnswered:         function() { this.sendNotAnswered(); },
      onToDevNullFromRing:     function() { this.sendDevNull('from ring'); },
      onToDevNullFromAnswered: function() { this.sendDevNull('from answered'); },

      sendOriginate: async function() {     
          console.debug('in sendOriginate()');   
          this.cdr.call_start = new Date();
          post(this.cdr);
      },

      sendRinging: async function() {        
          console.debug('in sendRinging()');
          this.cdr.ring_start = new Date();
          this.cdr.action = ACTIONS.RING;
          post(this.cdr);
      },

      sendAnswer: async function() {      
        console.debug('in sendAnswer()');  
        this.cdr.answer_start = new Date();
        this.cdr.action = ACTIONS.ANSWERED;
        post(this.cdr);
      },

      sendHangup: async function() {     
        console.debug('in sendHangup()');     
        this.cdr.call_end = new Date();
        this.cdr.duration = (this.cdr.call_end - this.cdr.call_start) / 1000;
        this.cdr.action = ACTIONS.HANGUP;
        post(this.cdr);
        MockCDR.add_record(this.cdr);
      },

      sendNotAnswered: async function() {        
        console.debug('in sendNotAnswered()');     
        this.cdr.call_end = new Date();
        this.cdr.duration = (this.cdr.call_end - this.cdr.call_start) / 1000;
        this.cdr.action = ACTIONS.HANGUP;
        post(this.cdr);
        MockCDR.add_record(this.cdr);
      },

      sendDevNull: async function(from) {
          console.debug('in a sendDevNull ' + from);
          console.debug('Call will not be finished with correct event, but CDR will be created');
          this.cdr.call_end = new Date();
          this.cdr.duration = (this.cdr.call_end - this.cdr.call_start) / 1000;
          this.cdr.action = ACTIONS.HANGUP;
          MockCDR.add_record(this.cdr);
      }
    }
});

const call = async (fsm) => {
    fsm.toOriginate();
    setTimeout(function() {
        fsm.toRing();
        setTimeout(function() {
            if (Helpers.mock_random(2) === 0) {
                fsm.toAnswer();
                setTimeout(function() {
                    if (Helpers.mock_random(100) < 96) {
                        fsm.toHangup();
                    } else {
                        fsm.toDevNullFromAnswered()
                    }
                }, 1000 + Helpers.mock_random(20000));
            } else {
                if (Helpers.mock_random(100) < 96) {
                    fsm.toNotAnswered();
                } else {
                    fsm.toDevNullFromRing()
                }
            }      
        }, 1000 + Helpers.mock_random(10000));
      }, 300 + Helpers.mock_random(3000));        
  }


const initialize = async () => {
    console.debug('In Initialize');
    
    let caller = PhoneNumbers.getRandomNumber();
    let callee = PhoneNumbers.getRandomNumber(caller);

    let fsm1 = new FSM(caller, callee);

    call(fsm1);
}

const post = async (obj) => {
    const instance = Axios.create({
        headers: {'Tenant-UUID': Settings.tenant}
    });

    instance.post(Settings.webhook_url, obj)
        .then((res) => {
            console.debug({response: res.data});
        })
        .catch((error) => {
            console.error(error)
        });
}

module.exports = {
    startCall,
    ACTIONS,
    DIRECTIONS
}