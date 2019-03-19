(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeMedicationRoutesSearch', TypeMedicationRoutesSearch);

    TypeMedicationRoutesSearch.$inject = ['$resource'];

    function TypeMedicationRoutesSearch($resource) {
        var resourceUrl =  'api/_search/type-medication-routes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
