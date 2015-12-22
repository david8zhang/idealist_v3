var express = require('express');

//Configure AWS DynamoDB
var AWS = require ('aws-sdk');
var dynamodb = new AWS.DynamoDB();

//Get all the users in the table
exports.getUsers = function(req, res) {   
};

//Post a user
exports.postUser = function(req, res) {
};

//Delete a user
exports.deleteUser = function(req, res) {
};
