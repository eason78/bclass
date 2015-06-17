var express = require('express');
var Bclass = require('../models/Bclass')
var Bullet = require('../models/bullet')
var Message = require('../models/message')
var router = express.Router();

generateBCode = function () {
  var alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

  var BCode = "";
  for (var i = 0; i < 6; i++) {
    BCode += alphanum.charAt(Math.floor(Math.random() * alphanum.length));
  }
  return BCode;
}

serverInternalError = function (response, msg) {
  console.log('[ERR]' + msg);
  response.sendStatus(500);
}

router.get('/', function (req, res, next) {
  res.send('Hello. This is B-class APP server!\nHave a nice day!');
});

/**
 * @return bcode
 */
router.get('/init', function (req, res, next) {
  newBClass = new Bclass({
    bcode: generateBCode()//,
    //createdAt: new Date()
  })
  newBClass.save(function (error, doc) {
    if (error) {
      console.log("B-class init failure.");
      serverInternalError(res, 'DB FAILURE: INSERT BClass');
    } else if (doc) {
      res.status(200).json({code: doc.bcode});
    }
  })

});

/**
 * @param  bcode
 * @param  limit
 * @return bullets
 * @coauthor Byron
 */
router.get('/get_bullets', function (req, res, next) {
  console.log(req.query);
  Bclass
    .find({bcode: req.query.bcode})
    .exec(function(error, docs) {
      if (docs == null || docs.length == 0) {
        console.log("Requested B-class not found");
        res.sendStatus(404);
      }
      else {
        Bullet
          .find({bcode: req.query.bcode})
          .where('read').equals(false)
          .limit(req.query.limit)
          .sort('+createdAt')
          .select('createdAt important fontSize fontColor texts')
          .exec(function(error, docs) {
            if (error) {
              serverInternalError(res, 'DB FAILURE: QUERY bullet');
            } 
            else {
              for (var i = 0; i < docs.length; i++) {
                Bullet.update(
                  {_id: docs[i]._id},
                  {$set: {read: true}},
                  function (error) {}
                );
              }
              res.status(200).json(docs);
            }
          });
      }
    }); 
});

/**
 * @param key
 * @param danmu
 * @coauthor Yu Deqiang
 */
router.post('/shoot', function(req, res) {
  console.log(req.body);
  Bclass.findOne({bcode: req.body.key}, function (error, doc) {
    if (error) {
      serverInternalError(res, 'DB FAILURE: QUERY BClass');
    } 
    else if (doc == null || doc.length == 0) {
      console.log("Requested B-class not found");
      res.sendStatus(404);
    } 
    else {
      newBullet = new Bullet({
        bcode: req.body.key,
        read: false,
        //createdAt: new Date(),
        important: req.body.important,
        fontSize: req.body.frontsize,
        fontColor: req.body.frontcolor,
        texts: req.body.danmu
      })
      newBullet.save(function (error) {
        if (error) {
          serverInternalError(res, 'DB FAILURE: INSERT bullet');
        }
        else {
          console.log("Bullet saved");
          res.sendStatus(200);
        }
      })
    }
  })
});

/**
 * @param bcode
 * @param message
 * @coauthor Byron
 */
router.post('/send_message', function(req, res) {
  console.log(req.body);
  Bclass.findOne({bcode: req.body.bcode}, function (error, doc) {
    if (error) {
      serverInternalError(res, 'DB FAILURE: QUERY BClass');
    } 
    else if (doc == null || doc.length == 0) {
      console.log("Requested B-class not found");
      res.sendStatus(404);
    } 
    else {
      newMessage = new Message({
        bcode: req.body.bcode,
        //createdAt: new Date(),
        texts: req.body.message
      })
      newMessage.save(function (error) {
        if (error) {
          serverInternalError(res, 'DB FAILURE: INSERT message');
        }
        else {
          console.log("Message saved");
          res.sendStatus(200);
        }
      })   
    }
  })
});

/**
 * @param  bcode
 * @return msgs
 * @coauthor Yu Deqiang
 */
router.get('/get_messages', function (req, res, next) {
  console.log(req.query);
  Bclass
    .find({bcode: req.query.key})
    .exec(function(error, docs) {
      if (docs == null || docs.length == 0) {
        console.log("Requested B-class not found");
        res.sendStatus(404);
      }
      else {
        Message
          .find({bcode: req.query.key})
          .sort('+createdAt')
          .select('createdAt texts')
          .exec(function(error, docs) {
            if (error) {
              serverInternalError(res, 'DB FAILURE: QUERY message');
            }
            else {
              res.status(200).json(docs);
            }
          });
      }
    });
});

module.exports = router;
