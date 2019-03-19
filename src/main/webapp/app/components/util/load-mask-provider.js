'use strict';

angular.module('bluebookApp')

/**
 * @ngdoc directive
 * @name loadMask
 * @author Alien Fernandez Fuentes <alien@kruger.com.ec>
 * @version 1.0.0
 *
 * @description Directive load mask application
 *
 */
    .directive('loadMask', loadMaskDirective)

    /**
     * @ngdoc provider
     * @name loadMask
     * @author Alien Fernandez Fuentes <alien@kruger.com.ec>
     * @version 1.0.0
     *
     * @description Provider load mask application
     *
     */
    .provider('loadMask', LoadMaskProvider);

/**
 * Load mask directive
 * @returns {{restrict: string, replace: boolean, template: string, link: Function}}
 */
function loadMaskDirective() {
    return {
        restrict: 'E',
        replace: true,
        template: '<div data-role="loadMask" class="load-mask">' +
        '<div class="load-mask-panel col-xs-12"><div class="loading-wrapper"><p class="loading-center">' +
        '       <i class="fa fa-spinner fa-pulse"></i> <span data-role="message"></span></p>' +
        '   </div>' +
        '</div></div>',
        link: function ($scope, element, $attrs, ngModel) {
            var elRender = ($attrs.el) ? $attrs.el : 'body';
            var message = ($attrs.message) ? $attrs.message : 'Espere por favor...';
            if ($attrs.id) {
                element.find('div[data-role="loadMask"]').attr('id', $attrs.id);
            }
            element.find('span[data-role="message"]').html(message);
            element.prependTo(elRender);
        }
    };
}

/**
 * Load mask provider
 * @constructor
 */
function LoadMaskProvider() {

    var defaultAppendEl = "body";
    var allExecPromises = null;
    // visible = false;

    /**
     * Set default append element
     * @param el
     */
    this.setDefaultAppendEl = function (el) {
        defaultAppendEl = el;
    };

    /**
     * Factory provider
     * @returns {{show: show, hide: hide, create: create}}
     */
    this.$get = ['$document', '$compile', '$rootScope', 'lodash', '$q', function ($document, $compile, $rootScope, _, $q) {
        return {
            show: show,
            hide: hide,
            create: create,
            setPromises: setPromises
        }

        /**
         * Show loading mask
         * @param selector
         */
        function show(selector) {
            // $rootScope.visibleMessages[selector] = true;
            if (angular.element(selector)) {
                angular.element(selector).show();
            }
        }

        /**
         * Hide loading mask
         * @param selector
         */
        function hide(selector) {
            // delete $rootScope.visibleMessages[selector];
            if (angular.element(selector)) {
                angular.element(selector).hide();
            }
        }

        /**
         * Create loading mask
         * @param id
         * @param message
         * @param appendEl
         * @param promises
         */
        function create(id, message, appendEl, promises) {
            appendEl = appendEl || defaultAppendEl;
            var selector = "#" + id;
            var template = angular.element('<load-mask id="' + id + '" el="' + appendEl + '" message="' + message + '" style="display: none"></load-mask>');
            var loadMaskExist = $document.find(selector);
            //Check exist mask in DOM
            if (!loadMaskExist.length) {
                $document.find(appendEl).prepend($compile(template)($rootScope));
            }
            //Check finally promise if apply and hide mask
            if (promises) {
                allExecPromises = promises;
                allPromises(allExecPromises).finally(function () {
                    hide(selector);
                });
            }
        }

        /**
         * Set promises
         * @param promises
         * @returns {*}
         */
        function setPromises(promises) {
            allExecPromises = promises;
        }

        /**
         * Check all promise is executed
         * @param promises
         * @returns {*}
         */
        function allPromises(promises) {
            if (_.isArray(promises)) {
                return $q.all(promises);
            }
            return promises;
        }
    }]
}
