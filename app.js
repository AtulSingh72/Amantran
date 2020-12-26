// Require Statement
var express = require("express"),
    app = express(),
    mongoose = require("mongoose"),
    bodyParser = require("body-parser"),
    session = require("express-session"),
    List = require("./models/List"),
    Guest = require("./models/Guest");
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
        if (err) res.json({ status: 404 });
        else {
            Guest.create(req.body, function (err, guest) {
                list.friends.push(guest);
                list.save();
                res.json({ status: 200 });
            });
        }
    });
});

app.post("/family/:id", function (req, res) {
    List.findById(req.params.id, function (err, list) {
        if (err) res.json({ status: 404 });
        else {
            Guest.create(req.body, function (err, guest) {
                list.family.push(guest);
                list.save();
                res.json({ status: 200 });
            });
        }
    });
});

app.get("/family/:id", function (req, res) {
    var list_o = List.findById(req.params.id);
    list_o.populate("family").exec(function (err, list) {
        if (err) res.json({ status: 404 });
        else {
            res.json({ family: list.family });
        }
    });
});

app.get("/friends/:id", function (req, res) {
    var list_o = List.findById(req.params.id);
    list_o.populate("friends").exec(function (err, list) {
        if (err) res.json({ status: 404 });
        else {
            res.json({ friends: list.friends });
        }
    });
});

app.delete("/:id1/friends/:id2", function (req, res) {
    List.findById(req.params.id1, function (err, list) {
        if (err) res.json({ status: 404 });
        Guest.findById(req.params.id2, function (err, guest) {
            if (err) res.json({ status: 404 });
            var len = list.friends.length;
            for (var i = 0; i < len; i++) {
                if (
                    list.friends[i]._id.toString() == req.params.id2.toString()
                ) {
                    list.friends.splice(i, 1);
                    break;
                }
            }
            list.save();
            guest.remove();
            res.json({ status: 200 });
        });
    });
});

app.delete("/:id1/family/:id2", function (req, res) {
    List.findById(req.params.id1, function (err, list) {
        if (err) res.json({ status: 404 });
        Guest.findById(req.params.id2, function (err, guest) {
            if (err) res.json({ status: 404 });
            var len = list.family.length;
            for (var i = 0; i < len; i++) {
                if (
                    list.family[i]._id.toString() == req.params.id2.toString()
                ) {
                    list.family.splice(i, 1);
                    break;
                }
            }
            list.save();
            guest.remove();
            res.json({ status: 200 });
        });
    });
});

app.put("/:id1/family/:id2", function (req, res) {
    Guest.findByIdAndUpdate(
        req.params.id2,
        { name: req.body.name, place: req.body.place },
        function (err, guest) {
            if (err) res.json({ status: 404 });
            else res.json({ status: 200 });
        }
    );
});

app.put("/:id1/friends/:id2", function (req, res) {
    Guest.findByIdAndUpdate(
        req.params.id2,
        { name: req.body.name, place: req.body.place },
        function (err, guest) {
            if (err) res.json({ status: 404 });
            else res.json({ status: 200 });
        }
    );
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
