package projet_ile;

interface Observer {
	/**
	 * Un observateur doit poss¨¦der une m¨¦thode [update] d¨¦clenchant la mise ¨¤
	 * jour.
	 */
	public void update();
	/**
	 * La version officielle de Java poss¨¨de des param¨¨tres pr¨¦cisant le
	 * changement qui a eu lieu.
	 */
}