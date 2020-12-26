var mongoose = require("mongoose");

var GuestSchema = new mongoose.Schema({
    name: String,
    place: String,
});

module.exports = mongoose.model("Guest", GuestSchema);
