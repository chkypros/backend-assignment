"use strict";

const cdr_uri = "/get_cdr"
const webhook_uri = "/trigger_webhooks"
const mock_post = "/post"

const Settings = require("./settings");
const MockCDR = require("./cdr_response")
const MockWebhook = require("./webhooks")

module.exports = [
    {
        method: "GET",
        path: "/",
        handler: (request, h) => {
            return "Nothing here, call " + cdr_uri;
        }
    },
    {
        method: "GET",
        path: cdr_uri,
        handler: (request, h) => {
            MockCDR.generate_data();
            return h.response(MockCDR.response())
                .header('Tenant-UUID', Settings.tenant);
        },
        config: {
            description: "Returns mocked CDR data"
        }
    },
    {
        method: "GET",
        path: webhook_uri,
        handler: (request, h) => {
            return MockWebhook.startCall();
        },
        config: {
            description: "Fires mocked webhooks. This will further call the default "
        }
    },
    {
        method: "POST",
        path: mock_post,
        handler: (request, h) => {
            return {payload: request.payload};
        },
        config: {
            description: "Internal use only"
        }
    }
];
