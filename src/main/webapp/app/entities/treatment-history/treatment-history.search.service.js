(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TreatmentHistorySearch', TreatmentHistorySearch);

    TreatmentHistorySearch.$inject = ['$resource'];

    function TreatmentHistorySearch($resource) {
        var resourceUrl =  'api/_search/treatment-histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
