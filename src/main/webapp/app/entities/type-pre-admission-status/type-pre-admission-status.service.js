(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePreAdmissionStatus', TypePreAdmissionStatus);

    TypePreAdmissionStatus.$inject = ['$resource'];

    function TypePreAdmissionStatus ($resource) {
        var resourceUrl =  'api/type-pre-admission-statuses/:id';

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
