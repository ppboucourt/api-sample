(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeContactFacilityType', TypeContactFacilityType);

    TypeContactFacilityType.$inject = ['$resource'];

    function TypeContactFacilityType ($resource) {
        var resourceUrl =  'api/type-contact-facility-types/:id';

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
