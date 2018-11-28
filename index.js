var express = require("express");
var bodyParser = require("body-parser");
const ngrok = require('ngrok');

var app = express();

var port = process.env.PORT || 3000;
var ip = process.env.IP || "127.0.0.1";

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post('/', function (req, res) {
    if (req.body.queryResult.action == "suma") {
        let num1 = parseFloat(req.body.queryResult.parameters.num1);
        let num2 = parseFloat(req.body.queryResult.parameters.num2);
        let suma = num1 + num2;
        response = num1 + " mas " + num2 + " es " + suma;
        res.json({
            "fulfillmentText": response
        });
    }
    if (req.body.queryResult.action == "resta") {
        let num1 = parseFloat(req.body.queryResult.parameters.num1);
        let num2 = parseFloat(req.body.queryResult.parameters.num2);
        let resta = num1 - num2;
        response = num1 + " menos " + num2 + " es " + resta;
        res.json({
            "fulfillmentText": response
        });
    }
    if (req.body.queryResult.action == "multi") {
        let num1 = parseFloat(req.body.queryResult.parameters.num1);
        let num2 = parseFloat(req.body.queryResult.parameters.num2);
        let mul = num1 * num2;
        response = num1 + " por " + num2 + " es " + mul;
        res.json({
            "fulfillmentText": response
        });
    }
    if (req.body.queryResult.action == "div") {
        let num1 = parseFloat(req.body.queryResult.parameters.num1);
        let num2 = parseFloat(req.body.queryResult.parameters.num2);
        let div = num1 / num2;
        response = num1 + " entre " + num2 + " es " + div;
        res.json({
            "fulfillmentText": response
        });
    }
});

app.listen(port, ip);

(async function () {
    const url = await ngrok.connect(port);
    console.log(url);
})();