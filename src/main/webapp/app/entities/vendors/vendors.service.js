(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Vendors', Vendors);

    Vendors.$inject = ['$resource'];

    function Vendors ($resource) {
        var resourceUrl =  'api/vendors/:id';

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
