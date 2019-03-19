(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('DateUtils', DateUtils);

    DateUtils.$inject = ['$filter'];

    function DateUtils($filter) {

        var service = {
            convertDateTimeFromServer : convertDateTimeFromServer,
            convertLocalDateFromServer : convertLocalDateFromServer,
            convertLocalDateToServer : convertLocalDateToServer,
            dateformat : dateformat,
            convertDateTimeFromServerM:convertDateTimeFromServerM,
            getAge: getAge
        };

        return service;

        function convertDateTimeFromServer(date) {
            if (date) {
                return new Date(date);
            } else {
                return null;
            }
        }

        function convertDateTimeFromServerM (date){
            if (date) {

                if(date.indexOf('Z')<0){
                    date = date + 'Z';
                }
                return new Date(date);      //return moment(date + 'Z');
            } else {
                return null;
            }
        }

        function convertLocalDateFromServer(date) {
            if (date) {
                var dateString = date.split('-');
                return new Date(dateString[0], dateString[1] - 1, dateString[2]);
            }
            return null;
        }

        function convertLocalDateToServer(date) {
            if (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            } else {
                return null;
            }
        }

        function dateformat () {
            return 'yyyy-MM-dd';
        }

        function getAge(dateBirth) {
            return (dateBirth) ? moment().diff(moment(dateBirth), 'years') : 0;
        }
    }

})();
