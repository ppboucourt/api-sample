(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GroupSessionDetailsSearch', GroupSessionDetailsSearch);

    GroupSessionDetailsSearch.$inject = ['$resource'];

    function GroupSessionDetailsSearch($resource) {
        var resourceUrl =  'api/_search/group-session-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
