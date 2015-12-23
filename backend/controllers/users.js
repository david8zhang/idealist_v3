var express = require('express');

//Configure AWS DynamoDB
var AWS = require ('aws-sdk');
var docClient = new AWS.DynamoDB.DocumentClient({region: 'us-west-2'});
var sha1 = require('sha1');

//Get all the users in the table
exports.getUsers = function(req, res) {   
};

//Post a user
exports.createUser = function(req, res) {

    var userid = sha1("user" + Math.floor(Date.now() / 1000).toString());
    var login_timestamp = Math.floor(Date.now()).toString();
    var email = req.body.name;
    var password = req.body.password;
    var username = req.body.username;
    var access_token = username + "&" + Math.floor(Date.now() / 1000).toString();
    var params = {};

    //Params for the user
    params.TableName = 'idealist-users';
    params.Item = {
        user_id: userid,
        login_timestamp,
        username: username,
        password: password,
        email: email,
        access_token: access_token
    };
    // Put the item
    docClient.putItem(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            res.json({ message: 'User was added!', data: user });
        }
    });
};

//Delete a user
exports.deleteUser = function(req, res) {
};

exports.authUser = function(req, res) {
};
