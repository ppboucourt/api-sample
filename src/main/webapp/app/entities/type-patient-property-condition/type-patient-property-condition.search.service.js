(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePatientPropertyConditionSearch', TypePatientPropertyConditionSearch);

    TypePatientPropertyConditionSearch.$inject = ['$resource'];

    function TypePatientPropertyConditionSearch($resource) {
        var resourceUrl =  'api/_search/type-patient-property-conditions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
