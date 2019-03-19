(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('DrugsSearch', DrugsSearch);

    DrugsSearch.$inject = ['$resource'];

    function DrugsSearch($resource) {
        var resourceUrl =  'api/_search/drugs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
