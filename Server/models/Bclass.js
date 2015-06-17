var mongoose = require('mongoose');

var bclassSchema = mongoose.Schema({
  bcode: String,
  createdAt: {type: Date}
});

var Bclass = mongoose.model('Bclass', bclassSchema);

module.exports = Bclass;
