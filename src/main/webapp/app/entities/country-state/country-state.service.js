(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('CountryState', CountryState);

    CountryState.$inject = ['$resource'];

    function CountryState ($resource) {
        var resourceUrl =  'api/country-states/:id';

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
