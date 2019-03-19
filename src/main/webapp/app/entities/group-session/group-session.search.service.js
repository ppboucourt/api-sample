(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GroupSessionSearch', GroupSessionSearch);

    GroupSessionSearch.$inject = ['$resource'];

    function GroupSessionSearch($resource) {
        var resourceUrl =  'api/_search/group-sessions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
