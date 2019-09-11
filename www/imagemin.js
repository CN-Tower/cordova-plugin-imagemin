module.exports = {
    compress: function(arg0, arg1, success, error) {
        if (typeof arg1 === 'object') {
            arg1 = JSON.stringify(arg1);
        } else if (typeof arg1 === 'function') {
            error = success, success = arg1, arg1 = "{}";
        }
        cordova.exec(success, error, 'ImageMin', 'compress', [arg0, arg1]);
    }
};