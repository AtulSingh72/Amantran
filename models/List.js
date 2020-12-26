var mongoose = require("mongoose");

var ListSchema = new mongoose.Schema({
    name: String,
    family: [{ type: mongoose.Schema.Types.ObjectId, ref: "Guest" }],
    friends: [{ type: mongoose.Schema.Types.ObjectId, ref: "Guest" }],
});

module.exports = mongoose.model("List", ListSchema);
