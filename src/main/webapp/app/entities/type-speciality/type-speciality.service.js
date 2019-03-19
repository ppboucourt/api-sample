(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeSpeciality', TypeSpeciality);

    TypeSpeciality.$inject = ['$resource'];

    function TypeSpeciality ($resource) {
        var resourceUrl =  'api/type-speciality/:id';

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
