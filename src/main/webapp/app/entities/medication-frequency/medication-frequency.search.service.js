(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('MedicationFrequencySearch', MedicationFrequencySearch);

    MedicationFrequencySearch.$inject = ['$resource'];

    function MedicationFrequencySearch($resource) {
        var resourceUrl =  'api/_search/medication-frequencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
