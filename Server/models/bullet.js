var mongoose = require('mongoose');

var bulletSchema = mongoose.Schema({
  bcode: String,
  read: Boolean,
  createdAt: {type: Date},
  texts: String
});

var Bullet = mongoose.model('Bullet', bulletSchema);

module.exports = Bullet;
