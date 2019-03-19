(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeMaritalStatus', TypeMaritalStatus);

    TypeMaritalStatus.$inject = ['$resource'];

    function TypeMaritalStatus ($resource) {
        var resourceUrl =  'api/type-marital-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
