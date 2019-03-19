(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePatientContactsRelationshipSearch', TypePatientContactsRelationshipSearch);

    TypePatientContactsRelationshipSearch.$inject = ['$resource'];

    function TypePatientContactsRelationshipSearch($resource) {
        var resourceUrl =  'api/_search/type-patient-contacts-relationships/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
