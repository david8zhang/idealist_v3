var express = require('express');
var AWS = require('aws-sdk');
var sha1 = require('sha1');
var docClient = new AWS.DynamoDB.DocumentClient({region: 'us-west-2'});

/** GET all ideas. */
exports.getIdeas = function(req, res) {
    var id = req.query.clusterid;
    var params = {};
    params.TableName = 'idealist_ideas';
    params.ExpressionAttributeValues = {
        ":clusterid":id
    };
    params.FilterExpression = "clusterid = :clusterid";
    docClient.scan(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            res.json(jsonString);
        }
    });
};

/** POST a new idea to the given cluster. */
exports.postIdea = function(req, res) {
    var ideaid = sha1("idea" + Math.floor(Date.now() / 1000).toString());

    console.log("Idea id = " + ideaid);
    
    var userid = req.body.userid;
    var name = req.body.name;
    var category = req.body.category;
    var description = req.body.description;
    
    //TODO: Add filters by category

    var idea_timestamp = Math.floor(Date.now()).toString();
    var comments = [];

    var params = {};
    params.TableName = 'idealist_ideas';
    params.Item = {
        idea_id: ideaid,
        idea_timestamp: idea_timestamp,
        name: name,
        category: category,
        description: description,
        comments: comments
    };
    docClient.put(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            console.log("Added new idea to idealist_ideas!");
            res.status(200).send('success!');
        }
    });

};

/** DELETE an idea from a given cluster. */
exports.deleteIdea = function(req, res) {
    var ideaid = req.body.ideaid;
    var idea_time = req.body.ideatimestamp;
    var params = {};
    params.TableName = "idealist_ideas";
    params.Key = {
        idea_id: ideaid,
        idea_timestamp: idea_time
    };
    docClient.delete(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            res.status(200).send('Idea successfully deleted!');
        }
    });
};

