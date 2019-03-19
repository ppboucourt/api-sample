(function () {
    'use strict';

    angular
        .module('udt')
        .factory('Utils', Utils);

    function Utils() {

        var service = {
            objectToArray: objectToArray,
            arrayToString: arrayToString,
            objectRoleToArray: objectRoleToArray,
        };

        return service;

        /**
         * Return object fields as array.
         *
         * @param object
         */
        function objectToArray(object) {

            var toArray = [];
            for (var key in object) {
                if (object.hasOwnProperty(key)) {
                    toArray.push(object[key]);
                }
            }
            return toArray;
        }

        /**
         * Return a string comma separated of array elements.
         *
         * @param Array
         * @return String comma separated or empty string
         */
        function arrayToString(inArray) {

            var toString = '';
            for (var i = 0; i < inArray.length; i++) {
                if (i === 0) {
                    toString = inArray[i];
                } else {
                    toString += ',' + inArray[i];
                }
            }
            return toString;
        }

        /**
         * Return a array of Roles as simple strings.
         *
         * [{
         *      name: 'ROLE1'
         * },{
         *      name: 'ROLE2'
         * }]
         *
         * @param Array of Roles as Objects
         * @return Array of Roles as String
         *
         * ['ROLE1', 'ROLE2']
         */
        function objectRoleToArray(inArray) {

            var toArray = [];
            for (var i = 0; i < inArray.length; i++) {
                toArray.push(inArray[i].name);
            }
            return toArray;
        }

    }
})();
