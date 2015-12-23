var express = require('express');

//Configure AWS DynamoDB
var AWS = require ('aws-sdk');
var docClient = new AWS.DynamoDB.DocumentClient({region: 'us-west-2'});
var sha1 = require('sha1');
var authController = require('../controllers/authcontroller');

//Post a user
exports.createUser = function(req, res) {

    var userid = sha1("user" + Math.floor(Date.now() / 1000).toString());
    var login_timestamp = Math.floor(Date.now()).toString();
    var email = req.body.name;
    var password = req.body.password;
    var username = req.body.username;
    var access_token = Math.floor(Date.now() / 1000).toString();
    var params = {};

    //Params for the user
    params.TableName = 'idealist_users';
    params.Item = {
        user_id: userid,
        login_timestamp,
        username: username,
        password: password,
        email: email,
        access_token: access_token
    };
    // Put the item
    docClient.put(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            res.status(200).send("Success! User was created!");
        }
    });
};

exports.authUser = function(req, res) {
    var username = req.body.username;
    var password = req.body.password;

    var params = {};
    params.TableName = 'idealist_users';
    params.FilterExpression = "username=:username AND password=:password";
    params.ExpressionAttributeValues = {
        ':username': username,
        ':password': password,
    };
    docClient.scan(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            var jsonString = JSON.parse(JSON.stringify(data));
            console.log(jsonString);
            if(jsonString["Count"] == 0) {
                res.status(403).send('Account does not exist!');
            } else {
                var user_token = data.Items[0].access_token;
                res.send(user_token);
            }
        }
    });

};
