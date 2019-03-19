(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TablesSearch', TablesSearch);

    TablesSearch.$inject = ['$resource'];

    function TablesSearch($resource) {
        var resourceUrl =  'api/_search/tables/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
