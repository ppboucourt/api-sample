(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ShiftsSearch', ShiftsSearch);

    ShiftsSearch.$inject = ['$resource'];

    function ShiftsSearch($resource) {
        var resourceUrl =  'api/_search/shifts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
