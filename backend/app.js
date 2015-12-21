/*
* @Author: David Zhang
* @Description: A NodeJS REST api for IdeaList
*/

var express = require('express');
var app = express();
var port = process.env.PORT || 8800;
var path = require('path');
var passport = require('passport');
var flash = require('connect-flash');

var morgan = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var session = require('express-session');

//Configure the router
var router = require('./routes/router.js');

//set up express
app.use(morgan('dev'));
app.use(cookieParser());


//Parse the application's json data
app.use(bodyParser.json());

//Use multipart/form data
app.use(bodyParser.urlencoded( { extended: true }));

//override with the X-HTTP-Method-Override header in the request to simulate a DELETE/PUT request
app.use(methodOverride('X-HTTP-Method-Override'));

//Routes
app.use('/api', router);

//Launch
app.listen(port);
console.log('Listening on port ' + port);

exports = module.exports = app;

