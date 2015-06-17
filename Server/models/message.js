var mongoose = require('mongoose');

var messageSchema = mongoose.Schema({
  bcode: String,
  createdAt: {type: Date},
  texts: String
});

var Message = mongoose.model('Message', messageSchema);

module.exports = Message;
