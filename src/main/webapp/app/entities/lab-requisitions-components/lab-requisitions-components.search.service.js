(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('LabRequisitionsComponentsSearch', LabRequisitionsComponentsSearch);

    LabRequisitionsComponentsSearch.$inject = ['$resource'];

    function LabRequisitionsComponentsSearch($resource) {
        var resourceUrl =  'api/_search/lab-requisitions-components/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
