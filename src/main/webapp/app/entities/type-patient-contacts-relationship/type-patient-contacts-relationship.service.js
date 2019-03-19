(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePatientContactsRelationship', TypePatientContactsRelationship);

    TypePatientContactsRelationship.$inject = ['$resource'];

    function TypePatientContactsRelationship ($resource) {
        var resourceUrl =  'api/type-patient-contacts-relationships/:id';

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
