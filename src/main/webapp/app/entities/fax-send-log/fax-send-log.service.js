(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('FaxSendLog', FaxSendLog);

    FaxSendLog.$inject = ['$resource', 'DateUtils'];

    function FaxSendLog ($resource, DateUtils) {
        var resourceUrl =  'api/fax-send-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': {url: 'api/fax-send-logs-vo/:fId', method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.sendDate = DateUtils.convertDateTimeFromServer(data.sendDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'updateStatus': {url: 'api/fax-send-logs-update', method: 'GET'}
        });
    }
})();
