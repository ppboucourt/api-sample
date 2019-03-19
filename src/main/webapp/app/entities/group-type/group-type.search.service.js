(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GroupTypeSearch', GroupTypeSearch);

    GroupTypeSearch.$inject = ['$resource'];

    function GroupTypeSearch($resource) {
        var resourceUrl =  'api/_search/group-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
