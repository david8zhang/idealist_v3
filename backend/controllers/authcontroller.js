//Configure passport
var passport = require('passport');
var passportBearer = require('passport-http-bearer');
var AWS = require('aws-sdk');
var docClient = new AWS.DynamoDB.DocumentClient({ region: 'us-west-2' });


passport.use(new passportBearer.Strategy(
    function(access_token, done) {
        var params = {};
        params.TableName = 'idealist_users';
        params.FilterExpression = "access_token=:access_token";
        params.ExpressionAttributeValues  = {
            ':access_token': access_token,
        };
        docClient.scan(params, function(err, data) {
            if(err) {
                return done(err);
            } else {
                var jsonString = JSON.parse(JSON.stringify(data));
                console.log(jsonString);
                if(jsonString["Count"] == 0) {
                    return done(null, false);
                } else {
                    var user_id = data.Items[0].user_id;
                    return done(null, user_id, { scope : 'all'});
                }
            }
        });
    })
);


exports.authToken = passport.authenticate('bearer', { session: false });
