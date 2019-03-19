(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('LabCompendiumSearch', LabCompendiumSearch);

    LabCompendiumSearch.$inject = ['$resource'];

    function LabCompendiumSearch($resource) {
        var resourceUrl =  'api/_search/lab-compendiums/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
