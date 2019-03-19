(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('LabRequisitionSearch', LabRequisitionSearch);

    LabRequisitionSearch.$inject = ['$resource'];

    function LabRequisitionSearch($resource) {
        var resourceUrl =  'api/_search/lab-requisitions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
