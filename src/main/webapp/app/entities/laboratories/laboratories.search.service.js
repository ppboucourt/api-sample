(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('LaboratoriesSearch', LaboratoriesSearch);

    LaboratoriesSearch.$inject = ['$resource'];

    function LaboratoriesSearch($resource) {
        var resourceUrl =  'api/_search/laboratories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
