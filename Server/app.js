var express = require('express'),
    path = require('path'),
    favicon = require('serve-favicon'),
    logger = require('morgan'),
    cookieParser = require('cookie-parser'),
    bodyParser = require('body-parser'),
    session = require('express-session'),
    // ORM for MongoDB
    mongoose = require('mongoose'),
    // Client for Redis
    redis = require('redis'),
    passport = require('passport');//,
    //WechatStrategy = require('passport-wechat');

var routes = require('./routes/index');
mongoose.connect('mongodb://localhost/B-class');

// User Authentication
/*
passport.serializeUser(function (user, done) {
    done(null, user);
});
passport.deserializeUser(function (obj, done) {
    done(null, obj);
});
passport.use(new WechatStrategy({
    //appid: 'wx0ff7006738630a6c',
    //appsecret: '866796103d71f653d69809cf1e8c2dae',
    //callbackURL: 'http://192.168.1.70:3000/auth/wechat/callback',
    scope: 'snsapi_base',
    state: true
    // appid: 'wx3af1ba5b6113419d',
    // appsecret: '74c7bf3702ff7d2cbc554ce19248a4b7',
    // callbackURL: 'http://api.liangyali.com:3000/auth/wechat/callback'
}, function (openid, profile, token, done) {
    return done(null, openid, profile);
}));
*/

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(session({secret: 'supernova', saveUninitialized: true, resave: true}));
app.use(express.static(path.join(__dirname, 'public')));

//app.use(passport.initialize());
//app.use(passport.session());

// session persisted message middleware
app.use(function(req, res, next) {
  var err = req.session.error,
      msg = req.session.notice,
      success = req.session.success;

  delete req.session.error;
  delete req.session.success;
  delete req.session.notice;

  if (err) res.locals.error = err;
  if (msg) res.locals.notice = msg;
  if (success) res.locals.success = success;

  next();
});

app.use('/', routes);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

module.exports = app;

