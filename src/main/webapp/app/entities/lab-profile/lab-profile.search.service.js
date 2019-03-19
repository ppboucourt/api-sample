(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('LabProfileSearch', LabProfileSearch);

    LabProfileSearch.$inject = ['$resource'];

    function LabProfileSearch($resource) {
        var resourceUrl =  'api/_search/lab-profiles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
