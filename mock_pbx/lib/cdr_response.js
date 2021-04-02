"use strict";

const Settings = require("./settings")
const Helpers = require("./helper")
const PhoneNumbers = require("./phone_numbers")

const cdr_data = [];

const response = () => {
    return {
        total: cdr_data.length,
        data: cdr_data
    }
}

const add_record = (r) => {
    cdr_data.push(r);
}

const generate_data = (amount = 5) => {
    let d = new Date();
    
    if (cdr_data.length) {
        let latest_d = cdr_data[cdr_data.length - 1].call_start;
        d = new Date(latest_d.toUTCString());
    }

    for (let c = 0; c < amount; c++) {
        d.setHours(d.getHours() + 1);
        let cdr = generate_record(d);
        cdr_data.push(cdr);
    }
}
  
const generate_record = (d) => {
    let caller = PhoneNumbers.getRandomNumber();
    let callee = PhoneNumbers.getRandomNumber(caller);

    let t = Helpers.generate_times(d);

    return {
        uuid: Helpers.uuidv4(),
        domain_name: Settings.domain,
        caller_name: "",
        caller_number: caller.number,
        destination_number: callee.number,
        direction: Helpers.mock_random(2) === 0 ? "outbound" : "inbound",
        call_start: t.call_start,
        ring_start: t.ring_start,
        answer_start: t.answer_start,
        call_end: t.call_end,
        duration: t.duration,
        recording: Helpers.uuidv4(),
        click_to_call: false,
        click_to_call_data: "",
        action: "HANGUP"
    };
}


module.exports = {
    data: cdr_data,
    response,
    add_record,
    generate_data
}
