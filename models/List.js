var mongoose = require("mongoose");

var ListSchema = new mongoose.Schema({
    name: String,
    family: [{ type: String }],
    friends: [{ type: String }],
});

module.exports = mongoose.model("List", ListSchema);
