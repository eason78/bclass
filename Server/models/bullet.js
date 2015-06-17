var mongoose = require('mongoose');

var bulletSchema = mongoose.Schema({
  bcode: String,
  read: Boolean,
  createdAt: {type: Date},
  fontSize: Number,
  fontColor: String,
  texts: String
});

var Bullet = mongoose.model('Bullet', bulletSchema);

module.exports = Bullet;
