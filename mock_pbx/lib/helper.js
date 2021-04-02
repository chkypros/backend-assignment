"use strict";

const mock_random = (length, exclude) => {
    let key = 0
    do {
      key = Math.random() * length << 0
    } while (key === exclude)
    return key
}

const uuidv4 = () => {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

const generate_times = (d) => {
    let call_start = new Date(d.toUTCString());
    let ring_start = new Date(call_start.toUTCString());
    ring_start.setSeconds(call_start.getSeconds() + mock_random(4) + 1);
    let answer_start = new Date(ring_start.toUTCString());
    answer_start.setSeconds(ring_start.getSeconds() + mock_random(6) + 1);
    let call_end = new Date(ring_start.toUTCString());
    call_end.setMinutes(answer_start.getMinutes() + mock_random(8) + 1);

    return {
        call_start: call_start,
        ring_start: ring_start,
        answer_start: answer_start,
        call_end: call_end,
        duration: (call_end - call_start) / 1000,
    }
}

module.exports = {
    uuidv4,
    mock_random,
    generate_times
}
