(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientActionTakeSearch', PatientActionTakeSearch);

    PatientActionTakeSearch.$inject = ['$resource'];

    function PatientActionTakeSearch($resource) {
        var resourceUrl =  'api/_search/patient-action-takes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
