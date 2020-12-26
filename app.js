// Require Statement
var express = require("express"),
    app = express(),
    mongoose = require("mongoose"),
    bodyParser = require("body-parser"),
    session = require("express-session"),
    List = require("./models/List");
mongoose.connect(
    "mongodb+srv://atulas72:mybdayon0204@cluster0.17haa.mongodb.net/amantran?retryWrites=true&w=majority",
    { useUnifiedTopology: false, useNewUrlParser: true }
);
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/", function (req, res) {
    var list = List.find({});
    list.populate().exec(function (err, listCom) {
        let JSON_Response = {
            title: "Amantran",
            listItems: listCom.length,
            list: listCom,
        };
        res.json(JSON_Response);
    });
});

app.post("/name", function (req, res) {
    List.create(req.body, function (err, list) {
        if (err) res.json({ status: "404" });
        else res.json({ status: "200", id: list._id });
    });
});

app.get("/name", function (req, res) {
    var list = List.find({});
    list.populate().exec(function (err, lists) {
        res.json({ lists: lists });
    });
});

app.post("/friends/:id", function (req, res) {
    List.findById(req.params.id, function (err, list) {
        if (err) res.json({ status: 200 });
        else {
            list.friends.push(req.body.entry);
            list.save();
            res.json({ status: 200 });
        }
    });
});

app.post("/family/:id", function (req, res) {
    List.findById(req.params.id, function (err, list) {
        if (err) res.json({ status: 200 });
        else {
            list.family.push(req.body.entry);
            list.save();
            res.json({ status: 200 });
        }
    });
});

app.get("/family/:id", function (req, res) {
    List.findById(req.params.id, function (err, list) {
        if (err) res.json({ status: 404 });
        else {
            res.json({ family: list.family });
        }
    });
});

app.get("/friends/:id", function (req, res) {
    List.findById(req.params.id, function (err, list) {
        if (err) res.json({ status: 404 });
        else {
            res.json({ friends: list.friends });
        }
    });
});

app.get("/userlist", function (req, res) {
    List.find({}, function (err, lists) {
        var listMap = {};
        lists.forEach(function (list) {
            listMap[list._id] = list.name;
        });
        res.json({ userList: listMap });
    });
});

app.listen(process.env.PORT || 8000, process.env.IP, function () {
    console.log("Server is listening...");
});
