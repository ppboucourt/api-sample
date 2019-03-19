(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('CareManagerSearch', CareManagerSearch);

    CareManagerSearch.$inject = ['$resource'];

    function CareManagerSearch($resource) {
        var resourceUrl =  'api/_search/care-managers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
