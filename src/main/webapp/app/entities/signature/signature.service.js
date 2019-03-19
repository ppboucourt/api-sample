(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Signature', Signature);

    Signature.$inject = ['$resource', 'DateUtils'];

    function Signature ($resource, DateUtils) {
        var resourceUrl =  'api/signatures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
