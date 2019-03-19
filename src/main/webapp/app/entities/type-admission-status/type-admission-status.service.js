(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeAdmissionStatus', TypeAdmissionStatus);

    TypeAdmissionStatus.$inject = ['$resource'];

    function TypeAdmissionStatus ($resource) {
        var resourceUrl =  'api/type-admission-statuses/:id';

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
