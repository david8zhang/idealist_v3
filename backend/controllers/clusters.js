var express = require('express');
var AWS = require('aws-sdk');
var sha1 = require('sha1');
var docClient = new AWS.DynamoDB.DocumentClient({region: 'us-west-2'});

/** GET all clusters that belong to a given userid. */
exports.getClusters = function(req, res) {
    var id = req.query.userid;
    var params = {};
    params.TableName = 'idealist_cluster';
    params.ExpressionAttributeValues = {
        ":userid": id
    }
    params.FilterExpression = "userid = :userid";
    docClient.scan(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            var jsonString = JSON.parse(JSON.stringify(data));
            res.json(jsonString);
        }
    });
};


/** POST a new cluster. */
exports.postCluster = function(req, res) {
    var clusterid = sha1("clus" + Math.floor(Date.now() / 1000).toString());
    var userid = req.body.userid;
    var name = req.body.name;
    var description = req.body.description;
    var cluster_timestamp = Math.floor(Date.now()).toString();
    var idea_ids = [];
    var params = {};
    params.TableName = 'idealist_cluster';
    params.Item = {
        cluster_id : clusterid,
        user_id: userid,
        name: name,
        description: description,
        idea_ids: idea_ids,
        cluster_timestamp: cluster_timestamp
    };
    docClient.put(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            console.log("Added new cluster to idealist_clusters!");
            res.status(200).send('success!');
        }
    })

};

/** DELETE a cluster. */
exports.deleteCluster = function(req, res) {
    var clusterid = req.body.clusterid;
    var cluster_time = req.body.clustertimestamp;
    var params = {};
    params.TableName = "idealist_cluster";
    params.Key = {
        cluster_id: clusterid,
        cluster_timestamp: cluster_time
    };
    docClient.delete(params, function(err, data) {
        if(err) {
            res.send(err);
        } else {
            res.status(200).send('success!');
        }
    })

};


