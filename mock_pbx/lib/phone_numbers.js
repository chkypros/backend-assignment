"use strict";

const Helpers = require("./helper")
const { ECANCELED } = require('constants');
const csv = require('csv-parser');
const fs = require('fs');

const data = []
var keys = []

const load = () => {
    if (!data.length) {
        fs.createReadStream('./pb_directory.csv')
            .pipe(csv())
            .on('data', (row) => {
                row.number = parseInt(row.number);
                data.push(row);
            })
            .on('end', () => {
                keys = Object.keys(data);
                console.log('Directory CSV file loaded');
            })
            .on('error', (e) => {
                console.log('Problem loading file: ' + e.message);
            });    
    }

    return;
}

const getRandomNumber = (exclude) => {
    let phone;

    let unique = (n) => Array.isArray(exclude)
        ? !exclude.includes(n)
        : (n) => n !== exclude;

    do {
        let key = Helpers.mock_random(keys.length);
        phone = data[keys[key]];
    
    } while (!unique(phone))

    return phone;
}


module.exports = {    
    load,
    getRandomNumber
}
